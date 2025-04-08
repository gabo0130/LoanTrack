import Image from 'next/image';
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { EyeIcon, MoreHorizontal } from 'lucide-react';
import { TableCell, TableRow } from '@/components/ui/table';
import { SelectCliente } from '@/models/cliente';
import ModalAddCliente from './ClienteForm';
import { verCreditosCliente } from './actions';
import { formatMoney } from '@/components/utils/formatUtils';

export function Cliente({ cliente, onVerDetalles }: { cliente: SelectCliente, onVerDetalles: (id: string) => void }) {

  return (
    <TableRow>
      <TableCell className="font-medium">{cliente.nombre}</TableCell>
      <TableCell>
        <Badge variant="outline" className="capitalize">
          {cliente.estado}
        </Badge>
      </TableCell>
      <TableCell>{cliente.cedula}</TableCell>
      <TableCell>{cliente.direccion}</TableCell>
      <TableCell>{cliente.telefono}</TableCell>
      <TableCell>{formatMoney(cliente.tope_credito)}</TableCell>
      <TableCell>
          <ModalAddCliente cliente={cliente} />
          <Button onClick={() => onVerDetalles(`${cliente.id}`)} variant="ghost" size="icon" title="Ver detalles">
            <EyeIcon className="h-4 w-4" />
          </Button>
        {/* <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button aria-haspopup="true" size="icon" variant="ghost">
              <MoreHorizontal className="h-4 w-4" />
              <span className="sr-only">Toggle menu</span>
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent align="end">
            <DropdownMenuLabel>Actions</DropdownMenuLabel>
            <DropdownMenuItem></DropdownMenuItem>
            <DropdownMenuItem>
              <Button
                onClick={() => verCreditosCliente(cliente.id)}
                variant="ghost"
              >
                Ver Créditos
              </Button>
            </DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu> */}
      </TableCell>
    </TableRow>
  );
}
