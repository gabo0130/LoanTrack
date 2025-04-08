import Image from 'next/image';
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { EyeIcon, MoreHorizontal } from 'lucide-react';
import { TableCell, TableRow } from '@/components/ui/table';
import { SelectProducto } from '@/models/producto';
import ModalAddProducto from './ProductoForm';
import { formatMoney } from '@/components/utils/formatUtils';

export function Producto({ producto, onVerDetalles }: { producto: SelectProducto, onVerDetalles: (id: string) => void }) {

  return (
    <TableRow>
      <TableCell className="font-medium">{producto.nombre}</TableCell>
      <TableCell>{producto.descripcion}</TableCell>
      <TableCell>
        <Badge variant="outline" className="capitalize">
          {producto.precio}
        </Badge>
      </TableCell>
      <TableCell>
          <ModalAddProducto producto={producto} />
          {/* <Button onClick={() => onVerDetalles(`${producto.id}`)} variant="ghost" size="icon" title="Ver detalles">
            <EyeIcon className="h-4 w-4" />
          </Button> */}
      </TableCell>
    </TableRow>
  );
}
