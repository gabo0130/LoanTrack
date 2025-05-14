import {
  Modal,
  ModalTrigger,
  ModalContent,
  ModalHeader,
  ModalTitle,
  ModalFooter,
  ModalDescription,
  ModalClose,
} from "@/components/ui/modal";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { useState } from "react";

interface Props {
  creditoId: number;
  onSuccess: () => void;
}


async function registrarAbono(creditoId: number, monto: number) {
  const response = await fetch(`/api/creditos/${creditoId}/abonos`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ monto }),
  });

  if (!response.ok) {
    throw new Error("Error al registrar el abono");
  }

  return response.json();
}

export function AbonarModal({ creditoId, onSuccess }: Props) {
  const [monto, setMonto] = useState("");

  const handleAbonar = async () => {
    const ok = await registrarAbono(creditoId, Number(monto));
    if (ok) {
      onSuccess();
    }
  };

  return (
    <Modal>
      <ModalTrigger asChild>
        <Button variant="ghost" size="icon" title="Abonar">
          💰
        </Button>
      </ModalTrigger>
      <ModalContent>
        <ModalHeader>
          <ModalTitle>Registrar Abono</ModalTitle>
          <ModalDescription>Ingresa el monto a abonar.</ModalDescription>
        </ModalHeader>
        <div className="py-4">
          <Input
            type="number"
            placeholder="Monto"
            value={monto}
            onChange={(e) => setMonto(e.target.value)}
          />
        </div>
        <ModalFooter>
          <ModalClose asChild>
            <Button variant="outline">Cancelar</Button>
          </ModalClose>
          <Button onClick={handleAbonar}>Guardar</Button>
        </ModalFooter>
      </ModalContent>
    </Modal>
  );
}
