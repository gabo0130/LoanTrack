'use client';

import { useParams } from 'next/navigation';
import { useEffect, useState } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow
} from '@/components/ui/table';
import { clientes,SelectCliente } from '@/models/cliente';
import { SelectCredito } from '@/models/credito';
import { SelectProducto } from '@/models/producto';
import { useLoading } from '@/components/LoadingProvider';
import { formatDate, formatMoney } from '@/components/utils/formatUtils';
interface CreditoProducto {
  credito: SelectCredito;
  producto: SelectProducto;
}

interface ClienteDetalle {
  cliente: SelectCliente;
  creditos: CreditoProducto[];
}

export default function ClienteDetail() {
  const params = useParams<{ clienteId: string }>();
  const [clienteDetalle, setClienteDetalle] = useState<ClienteDetalle | null>(
    null
  );
  
  const { setLoading } = useLoading();
  useEffect(() => {
    async function fetchCliente() {
      setLoading(true);
      try {
        console.log(params.clienteId);
        const res = await fetch(`/api/cliente/${params.clienteId}`, {
          method: 'GET',
          headers: { 'Content-Type': 'application/json' }
        }).then((res) => res.json());
        const data = (res) as ClienteDetalle;
        console.log(data);
        setClienteDetalle(data);
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    }
    fetchCliente();
  }, [params.clienteId]);

  const totalCreditos =
    clienteDetalle?.creditos.reduce((acc: number, credito) => acc + Number(credito.credito.monto_total), 0) || 0;


  if (!clienteDetalle) return <div>Cliente no encontrado</div>;

  return (
    <Card>
      <CardHeader>
        <CardTitle>Detalle del Cliente</CardTitle>
      </CardHeader>
      <CardContent>
        <div className="mb-4">
          <p>
            <strong>Nombre:</strong> {clienteDetalle.cliente.nombre}
          </p>
          <p>
            <strong>Cédula:</strong> {clienteDetalle.cliente.cedula}
          </p>
          <p>
            <strong>Dirección:</strong> {clienteDetalle.cliente.direccion}
          </p>
          <p>
            <strong>Teléfono:</strong> {clienteDetalle.cliente.telefono}
          </p>
          <p>
            <strong>Es nuevo:</strong> {clienteDetalle.cliente.es_nuevo ? 'Sí' : 'No'}
          </p>
          <p>
            <strong>Estado:</strong> {clienteDetalle.cliente.estado}
          </p>
          <p>
            <strong>Tope credito:</strong> {formatMoney(clienteDetalle.cliente.tope_credito ?? 0)}
          </p>
        </div>

        <h2 className="text-lg font-semibold mb-2">Créditos</h2>
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>Tipo</TableHead>
              <TableHead>Producto/Dinero</TableHead>
              <TableHead>Fecha Inicio </TableHead>
              <TableHead>Monto</TableHead>
              <TableHead>Cuotas</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {clienteDetalle.creditos.map((CreditoProducto) => (
              <TableRow key={CreditoProducto.credito.id}>
                <TableCell>{CreditoProducto.credito.tipo_credito}</TableCell>
                <TableCell>
                  {CreditoProducto.credito.tipo_credito === 'PRODUCTO'
                    ? CreditoProducto.producto.nombre
                    : 'Dinero'}
                </TableCell>
                <TableCell>{formatDate(CreditoProducto.credito.fecha_inicio)}</TableCell>
                <TableCell>{formatMoney(CreditoProducto.credito.monto_total)}</TableCell>
                <TableCell>{CreditoProducto.credito.plazo_cuotas}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>

        <div className="mt-4 font-semibold">
          Total Créditos: {formatMoney(totalCreditos)}
        </div>
      </CardContent>
    </Card>
  );
}
