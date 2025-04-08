'use client';
import React, { useState, useEffect } from 'react';
import { useSearchParams } from 'next/navigation';
import { TabsContent } from '@/components/ui/tabs';
import { Creditostable } from './CreditoTable';
import { CreditosData } from '@/models/credito';
import { useLoading } from "@/components/LoadingProvider";
interface CreditosContentProps {
  reloadFlag: boolean;
}

interface creditosResponse {
  creditos: CreditosData[];
  newOffset: number | null;
  totalCreditos: number;
}

export default function CreditosContent({ reloadFlag }: CreditosContentProps) {
  const searchParams = useSearchParams();
  const { setLoading } = useLoading();
  const [creditos, setCreditos] = useState<CreditosData[]>([]);
  const [newOffset, setNewOffset] = useState<number | null>(0);
  const [totalCreditos, setTotalCreditos] = useState<number>(0);

  const fetchCreditos = async () => {
    setLoading(true);
    const search = searchParams.get('q') ?? '';
    const offset = searchParams.get('offset') ?? '0';

    const params = new URLSearchParams({
      q: search,
      offset
    });

    const res = await fetch(`/api/credito?${params.toString()}`, {
      cache: 'no-store'
    });

    if (!res.ok) {
      setLoading(false);
      throw new Error('Error al obtener los creditos');
    }

    const data = (await res.json()) as creditosResponse;

    console.log('Creditos:', data.creditos);

    setCreditos(data.creditos);
    setNewOffset(data.newOffset);
    setTotalCreditos(data.totalCreditos);
    setLoading(false);
  };

  useEffect(() => {
    fetchCreditos();
  }, [searchParams, reloadFlag]);

  return (
    <TabsContent value="all">
      <Creditostable
        creditos={creditos}
        offset={newOffset ?? 0}
        totalCreditos={totalCreditos}
      />
    </TabsContent>
  );
}