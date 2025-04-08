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
import { Producto } from './producto';
import { SelectProducto } from '@/models/producto';
import { useRouter } from 'next/navigation';
import { ChevronLeft, ChevronRight } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { calculatePagination } from '@/components/utils/paginationUtils';
export function Productostable({
  productos,
  offset,
  totalProductos
}: {
  productos: SelectProducto[];
  offset: number;
  totalProductos: number;
}) {
  let router = useRouter();
  const { start, end, total } = calculatePagination(productos.length,offset, totalProductos);
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

  return productos.length === 0 ? (
    <Card>
      <CardHeader>
        <CardTitle>Productos</CardTitle>
        <CardDescription>No hay productos disponibles</CardDescription>
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
        <CardTitle>Productos</CardTitle>
        <CardDescription></CardDescription>
      </CardHeader>
      <CardContent>
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>Nombre</TableHead>
              <TableHead>Descripcion</TableHead>
              <TableHead className="hidden md:table-cell">Precio</TableHead>
              <TableHead className="hidden md:table-cell">Acciones</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {productos.map((producto) => (
              <Producto key={producto.id} producto={producto} onVerDetalles={routerDetalles}/>
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
            de <strong>{total}</strong> productos
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
