'use client';

import { useState } from "react";
import { ClienteSearch } from "./ClienteSearch";
import { CreditoList } from "./CreditoList";
import { ClienteDetalle } from "@/models/cliente";

export default function PagosPage() {
  const [clienteDetalle, setClienteDetalle] = useState<ClienteDetalle | null>(null);

  const buscar = async (cedula: string) => {
    const data = await getClientePorCedula(cedula);
    setClienteDetalle(data);
  };

  const getClientePorCedula = async (cedula: string) => {
    const response = await fetch(`/api/cliente/search?cedula=${cedula}`);
    if (!response.ok) {
      throw new Error('Error fetching cliente');
    }
    return response.json();
  };

  return (
    <div >
      <ClienteSearch onBuscar={buscar} />
      {clienteDetalle && (
        <CreditoList creditos={clienteDetalle.creditos} onReload={() => buscar(clienteDetalle.cliente.cedula)} />
      )}
    </div>
  );
}
