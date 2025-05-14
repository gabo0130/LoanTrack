
import { AbonarModal } from "./AbonarModal";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Table, TableHeader, TableRow, TableHead, TableBody, TableCell } from "@/components/ui/table";
import { formatDate, formatMoney } from "@/components/utils/formatUtils";
import { CreditoProducto } from "@/models/credito";

interface Props {
  creditos: CreditoProducto[];
  onReload: () => void;
}

export function CreditoList({ creditos, onReload }: Props) {
  const total = creditos.reduce((sum, cp) => sum + Number(cp.credito.monto_total), 0);

  return (
    <Card>
      <CardHeader>
        <CardTitle>Créditos del Cliente</CardTitle>
      </CardHeader>
      <CardContent>
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>Tipo</TableHead>
              <TableHead>Producto</TableHead>
              <TableHead>Inicio</TableHead>
              <TableHead>Monto</TableHead>
              <TableHead>Saldo</TableHead>
              <TableHead>Abonar</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {creditos.map((cp) => (
              <TableRow key={cp.credito.id}>
                <TableCell>{cp.credito.tipo_credito}</TableCell>
                <TableCell>
                  {cp.credito.tipo_credito === "PRODUCTO"
                    ? cp.producto?.nombre ?? "N/A"
                    : "Dinero"}
                </TableCell>
                <TableCell>{formatDate(cp.credito.fecha_inicio)}</TableCell>
                <TableCell>{formatMoney(cp.credito.monto_total)}</TableCell>
                <TableCell>calcular</TableCell>
                <TableCell>
                  <AbonarModal
                    creditoId={cp.credito.id}
                    onSuccess={onReload}
                  />
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
        <div className="mt-4 font-semibold">
          Total Créditos: {formatMoney(total)}
        </div>
      </CardContent>
    </Card>
  );
}
