import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { useState } from "react";

interface Props {
  onBuscar: (cedula: string) => void;
}

export function ClienteSearch({ onBuscar }: Props) {
  const [cedula, setCedula] = useState('');

  return (
    <div className="w-full max-w-4xl mb-6 flex gap-4">
      <Input
        placeholder="Ingrese la cédula"
        value={cedula}
        onChange={(e) => setCedula(e.target.value)}
      />
      <Button onClick={() => onBuscar(cedula)}>Buscar</Button>
    </div>
  );
}
