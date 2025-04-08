import { pgTable, numeric, integer, timestamp, pgEnum, serial } from 'drizzle-orm/pg-core';
import { relations } from 'drizzle-orm';
import { productos, SelectProducto } from './producto';
import { clientes, SelectCliente } from './cliente';

export const tipoCreditoEnum = pgEnum('tipo_credito', ['PRODUCTO', 'DINERO']);
export const estadoCreditoEnum = pgEnum('estado_credito', ['abierto', 'cerrado', 'cancelado', 'moroso']);

export const creditos = pgTable('credito', {
  id: serial('id').primaryKey(),
  id_cliente: integer('id_cliente').notNull(),
  id_producto: integer('id_producto'),
  tipo_credito: tipoCreditoEnum('tipo_credito').notNull(),
  monto_total: numeric('monto_total', { precision: 12, scale: 2 }).notNull(),
  tasa_interes: numeric('tasa_interes', { precision: 5, scale: 2 }),
  plazo_cuotas: integer('plazo_cuotas').notNull(),
  estado: estadoCreditoEnum('estado').default('abierto'),
  fecha_inicio: timestamp('fecha_inicio').notNull(),
  fecha_fin: timestamp('fecha_fin')
});

export const creditoRelations = relations(creditos, ({ one }) => ({
  cliente: one(clientes, {
    fields: [creditos.id_cliente],
    references: [clientes.id],
  }),
  producto: one(productos, {
    fields: [creditos.id_producto],
    references: [productos.id],
  }),
}));

export type SelectCredito = typeof creditos.$inferSelect;

export interface CreditosData extends SelectCredito {
  cliente: SelectCliente;
  producto: SelectProducto;
}
