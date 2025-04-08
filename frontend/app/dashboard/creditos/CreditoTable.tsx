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
import { Credito } from './Credito';
import { CreditosData } from '@/models/credito';
import { useRouter } from 'next/navigation';
import { ChevronLeft, ChevronRight } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { calculatePagination } from '@/components/utils/paginationUtils';
export function Creditostable({
  creditos,
  offset,
  totalCreditos
}: {
  creditos: CreditosData[];
  offset: number;
  totalCreditos: number;
}) {
  let router = useRouter();
  const { start, end, total } = calculatePagination(creditos.length,offset, totalCreditos);
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

  return creditos.length === 0 ? (
    <Card>
      <CardHeader>
        <CardTitle>Creditos</CardTitle>
        <CardDescription>No hay creditos disponibles</CardDescription>
      </CardHeader>
      <CardFooter>
        <form className="flex items-center w-full justify-between">
          <div className="text-xs text-muted-foreground">
          </div>
          <div className="flex">
            <Button
              onClick={prevPage}
              variant="ghost"
              size="sm"
              type="submit"
            >
              <ChevronLeft className="mr-2 h-4 w-4" />
              Prev
            </Button>
            <Button
              onClick={nextPage}
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
        <CardTitle>Creditos</CardTitle>
        <CardDescription></CardDescription>
      </CardHeader>
      <CardContent>
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>Nombre cliente</TableHead>
              <TableHead className="hidden md:table-cell">Cedula</TableHead>
              <TableHead>Producto</TableHead>
              <TableHead>Estado</TableHead>
              <TableHead>Fecha</TableHead>
              <TableHead>Valor</TableHead>
              <TableHead>Cuotas</TableHead>
              <TableHead>Acciones</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {creditos.map((credito) => (
              <Credito key={credito.id} credito={credito} onVerDetalles={routerDetalles}/>
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
            de <strong>{total}</strong> creditos
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
