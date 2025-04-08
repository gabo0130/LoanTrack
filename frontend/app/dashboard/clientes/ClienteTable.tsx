'use client';

import {
  TableHead,
  TableRow,
  TableHeader,
  TableBody,
  Table
} from '@/components/ui/table';
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle
} from '@/components/ui/card';
import { Cliente } from './cliente';
import { SelectCliente } from '@/models/cliente';
import { useRouter } from 'next/navigation';
import { ChevronLeft, ChevronRight } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { calculatePagination } from '@/components/utils/paginationUtils';
export function Clientestable({
  clientes,
  offset,
  totalClientes
}: {
  clientes: SelectCliente[];
  offset: number;
  totalClientes: number;
}) {
  let router = useRouter();
  const { start, end, total } = calculatePagination(clientes.length,offset, totalClientes);
  function prevPage() {
    router.back();
  }

  function routerDetalles(id: string) {
    router.push(`${window.location.pathname}/${id}`);
  }

  function nextPage() {
    router.push(`${window.location.pathname}/?offset=${offset}`, {
      scroll: false
    });
  }

  return clientes.length === 0 ? (
    <Card>
      <CardHeader>
        <CardTitle>Clientes</CardTitle>
        <CardDescription>No hay clientes disponibles</CardDescription>
      </CardHeader>
      <CardFooter>
        <form className="flex items-center w-full justify-between">
          <div className="text-xs text-muted-foreground">
          </div>
          <div className="flex">
            <Button
              formAction={prevPage}
              variant="ghost"
              size="sm"
              type="submit"
            >
              <ChevronLeft className="mr-2 h-4 w-4" />
              Prev
            </Button>
            <Button
              formAction={nextPage}
              variant="ghost"
              size="sm"
              type="submit"
              disabled
            >
              Next
              <ChevronRight className="ml-2 h-4 w-4" />
            </Button>
          </div>
        </form>
      </CardFooter>
    </Card>
  ):(
    <Card>
      <CardHeader>
        <CardTitle>Clientes</CardTitle>
        <CardDescription></CardDescription>
      </CardHeader>
      <CardContent>
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>Nombre</TableHead>
              <TableHead>Estado</TableHead>
              <TableHead className="hidden md:table-cell">Cedula</TableHead>
              <TableHead className="hidden md:table-cell">Direccion</TableHead>
              <TableHead className="hidden md:table-cell">
                Numero Telefono
              </TableHead>
              <TableHead className="hidden md:table-cell">
                Monto maximo
              </TableHead>
              <TableHead className="hidden md:table-cell">Acciones</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {clientes.map((cliente) => (
              <Cliente key={cliente.id} cliente={cliente} onVerDetalles={routerDetalles}/>
            ))}
          </TableBody>
        </Table>
      </CardContent>
      <CardFooter>
        <form className="flex items-center w-full justify-between">
          <div className="text-xs text-muted-foreground">
            Mostrando{' '}
            <strong>
              {start}-{end}
            </strong>{' '}
            de <strong>{total}</strong> clientes
          </div>
          <div className="flex">
            <Button
              formAction={prevPage}
              variant="ghost"
              size="sm"
              type="submit"
              disabled={start === 1}
            >
              <ChevronLeft className="mr-2 h-4 w-4" />
              Prev
            </Button>
            <Button
              formAction={nextPage}
              variant="ghost"
              size="sm"
              type="submit"
              disabled={offset === 0}
            >
              Next
              <ChevronRight className="ml-2 h-4 w-4" />
            </Button>
          </div>
        </form>
      </CardFooter>
    </Card>
  );
}
