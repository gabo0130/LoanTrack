'use client';
import React, { useState, useEffect } from 'react';
import { useSearchParams } from 'next/navigation';
import { TabsContent } from '@/components/ui/tabs';
import { Clientestable } from './ClienteTable';
import { SelectCliente } from '@/models/cliente';
import { useLoading } from "@/components/LoadingProvider";
interface ClientesContentProps {
  reloadFlag: boolean;
}

export default function ClientesContent({ reloadFlag }: ClientesContentProps) {
  const searchParams = useSearchParams();
  const { setLoading } = useLoading();
  const [clientes, setClientes] = useState<SelectCliente[]>([]);
  const [newOffset, setNewOffset] = useState<number | null>(0);
  const [totalClientes, setTotalClientes] = useState<number>(0);

  const fetchClientes = async () => {
    setLoading(true);
    const search = searchParams.get('q') ?? '';
    const offset = searchParams.get('offset') ?? '0';

    const params = new URLSearchParams({
      q: search,
      offset
    });

    const res = await fetch(`/api/cliente?${params.toString()}`, {
      cache: 'no-store'
    });

    if (!res.ok) {
      setLoading(false);
      throw new Error('Error al obtener los clientes');
    }

    const data = (await res.json()) as {
      clientes: SelectCliente[];
      newOffset: number | null;
      totalClientes: number;
    };

    setClientes(data.clientes);
    setNewOffset(data.newOffset);
    setTotalClientes(data.totalClientes);
    setLoading(false);
  };

  useEffect(() => {
    fetchClientes();
  }, [searchParams, reloadFlag]);

  return (
    <TabsContent value="all">
      <Clientestable
        clientes={clientes}
        offset={newOffset ?? 0}
        totalClientes={totalClientes}
      />
    </TabsContent>
  );
}