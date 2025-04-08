import { BaseRepository } from '../baseRepository';
import { creditos, SelectCredito, CreditosData} from '@/models/credito';
import { db } from '../db';
import { ilike, count, eq } from 'drizzle-orm';
import { productos } from '@/models/producto';
import { clientes } from '@/models/cliente';
import { cuotas } from '@/models/cuota'; 
export class CreditoRepository extends BaseRepository<SelectCredito> {
  constructor() {
    super(creditos);
  }

  async searchCreditos(search: string) {
    return await db
      .select()
      .from(this.table)
      .where(ilike(this.table.nombre, `%${search}%`))
      .limit(1000);
  }

  async getCreditosWithPagination(search: string, offset: number) {
    if (search) {
      return {
        creditos: await this.searchCreditos(search),
        newOffset: null,
        totalCreditos: 0
      };
    }

    if (offset === null) {
      return { creditos: [], newOffset: null, totalCreditos: 0 };
    }

    const totalCreditos = await db.select({ count: count() }).from(this.table);
    const moreCreditos = await db
      .select()
      .from(this.table).leftJoin(clientes, eq(this.table.id_cliente, clientes.id))
      .leftJoin(productos, eq(this.table.id_producto, productos.id))
      .limit(5)
      .offset(offset);
    const newOffset = moreCreditos.length >= 5 ? offset + 5 : 0;

    
    const formattedCreditos = moreCreditos.map((credito: any) => ({
      id: credito.credito.id,
      id_cliente: credito.credito.id_cliente,
      id_producto: credito.credito.id_producto,
      tipo_credito: credito.credito.tipo_credito,
      monto_total: credito.credito.monto_total,
      tasa_interes: credito.credito.tasa_interes,
      plazo_cuotas: credito.credito.plazo_cuotas,
      estado: credito.credito.estado,
      fecha_inicio: credito.credito.fecha_inicio,
      fecha_fin: credito.credito.fecha_fin,
      cliente: credito.cliente,
      producto: credito.producto ?  credito.producto : null
    }));

    return {
      creditos: formattedCreditos,
      newOffset,
      totalCreditos: totalCreditos[0].count
    };
  }

  async getCreditoWithDetails(id: number) {
    const credito = await db
      .select()
      .from(this.table)
      .where(eq(this.table.id, id)).leftJoin(clientes, eq(this.table.id_cliente, clientes.id))
      .leftJoin(productos, eq(this.table.id_producto, productos.id))
      .limit(1);

      console.log('Credito:', credito);
    const res = {
      
      credito: credito[0],
      //details
    };
    return res;
  }

  async getCreditoById(id: number) {
    return await db
      .select()
      .from(this.table)
      .where(eq(this.table.id, id))
      .limit(1);
  }

  
async createCredito(credito: any) {
  // Crear el crédito y obtener el ID generado
  const createdCredito = await db
    .insert(this.table)
    .values({
      ...credito,
      fecha_inicio: credito.fecha_inicio ? new Date(credito.fecha_inicio) : null,
      fecha_fin: credito.fecha_fin ? new Date(credito.fecha_fin) : null,
    })
    .returning()
    .execute() as { id: number }[];

  const creditoId = createdCredito[0]?.id;
  if (!creditoId) {
    throw new Error('No se pudo crear el crédito.');
  }

  // Calcular las cuotas
  const cuotas = [];
  const fechaInicio = new Date(credito.fecha_inicio);
  const montoPorCuota = credito.monto_total / credito.plazo_cuotas;

  for (let i = 0; i < credito.plazo_cuotas; i++) {
    const fechaVencimiento = new Date(fechaInicio);
    fechaVencimiento.setDate(fechaInicio.getDate() + i * 8); // Incrementar cada 8 días

    cuotas.push({
      id_credito: creditoId,
      numero_cuota: i + 1,
      monto_cuota: montoPorCuota.toString(),
      fecha_vencimiento: fechaVencimiento.toISOString(),
      saldo_pendiente: montoPorCuota.toString(),
    });
  }

  // Insertar las cuotas en la base de datos
  await db.insert(this.table).values(cuotas.map(cuota => ({
    id_credito: cuota.id_credito,
    numero_cuota: cuota.numero_cuota,
    monto_cuota: cuota.monto_cuota,
    fecha_vencimiento: cuota.fecha_vencimiento,
    saldo_pendiente: cuota.saldo_pendiente,
  }))).execute();

  return createdCredito;
}
  

async updateCredito(id: number, credito: any) {
  // Actualizar el crédito
  const updatedCredito = await db
    .update(this.table)
    .set({
      ...credito,
      fecha_inicio: credito.fecha_inicio ? new Date(credito.fecha_inicio) : null,
      fecha_fin: credito.fecha_fin ? new Date(credito.fecha_fin) : null,
    })
    .where(eq(this.table.id, id))
    .returning()
    .execute();

  if (!Array.isArray(updatedCredito) || !updatedCredito.length) {
    throw new Error('No se pudo actualizar el crédito.');
  }

  // Validar y actualizar las cuotas 
  // ToDo: Se necesita validar si tiene cuotas pagadas porque ese monto se tiene que descontar 
  const existingCuotas = await db
    .select()
    .from(cuotas)
    .where(eq(cuotas.id_credito, id))
    .execute();

  const fechaInicio = new Date(credito.fecha_inicio);
  const montoPorCuota = credito.monto_total / credito.plazo_cuotas;

  if (
    existingCuotas.length !== credito.plazo_cuotas ||
    existingCuotas.some((cuota) => Number(cuota.monto_cuota) !== montoPorCuota)
  ) {
    await db.delete(cuotas).where(eq(cuotas.id_credito, id)).execute();

    const nuevasCuotas = [];
    for (let i = 0; i < credito.plazo_cuotas; i++) {
      const fechaVencimiento = new Date(fechaInicio);
      fechaVencimiento.setDate(fechaInicio.getDate() + i * 8); 

      nuevasCuotas.push({
        id_credito: id,
        numero_cuota: i + 1,
        monto_cuota: montoPorCuota.toString(),
        fecha_vencimiento: fechaVencimiento.toISOString(),
        saldo_pendiente: montoPorCuota.toString(),
      });
    }

    // Insertar las nuevas cuotas
    await db.insert(cuotas).values(nuevasCuotas).execute();
  }

  return updatedCredito;
}

  async deleteCredito(id: number) {
    return await db.delete(this.table).where(eq(this.table.id, id)).execute();
  }
}
