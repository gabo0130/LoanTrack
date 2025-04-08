import Image from 'next/image';
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuTrigger
} from '@/components/ui/dropdown-menu';
import { EyeIcon, MoreHorizontal } from 'lucide-react';
import { TableCell, TableRow } from '@/components/ui/table';
import { CreditosData, SelectCredito } from '@/models/credito';
import ModalAddCliente from './CreditoForm';
import { verCreditosCliente } from './actions';
import { formatDate, formatMoney } from '@/components/utils/formatUtils';

export function Credito({
  credito,
  onVerDetalles
}: {
  credito: CreditosData;
  onVerDetalles: (id: string) => void;
}) {
  return (
    <TableRow>
      <TableCell className="font-medium">{credito.cliente.nombre}</TableCell>

      <TableCell>{credito.cliente.cedula}</TableCell>
      <TableCell>{credito.producto ? credito.producto.nombre : credito.tipo_credito}</TableCell>
      <TableCell>
        <Badge variant="outline" className="capitalize">
          {credito.estado}
        </Badge>
      </TableCell>
      <TableCell>{formatDate(credito.fecha_inicio)}</TableCell>
      <TableCell>{formatMoney(credito.monto_total)}</TableCell>
      <TableCell>{credito.plazo_cuotas}</TableCell>
      <TableCell>
      <ModalAddCliente credito={credito} />
        {/* terminar pagina de detalles 
        <Button
          onClick={() => onVerDetalles(`${credito.id}`)}
          variant="ghost"
          size="icon"
          title="Ver detalles"
        >
          <EyeIcon className="h-4 w-4" />
        </Button> */}
      </TableCell>
    </TableRow>
  );
}
