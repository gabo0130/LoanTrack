'use client';
import React, { useState, useEffect } from 'react';
import { useSearchParams } from 'next/navigation';
import { TabsContent } from '@/components/ui/tabs';
import { SelectProducto } from '@/models/producto';
import { useLoading } from "@/components/LoadingProvider";
import { Productostable } from './ProductosTable';
interface ProductosContentProps {
  reloadFlag: boolean;
}

export default function ProductosContent({ reloadFlag }: ProductosContentProps) {
  const searchParams = useSearchParams();
  const { setLoading } = useLoading();
  const [productos, setProductos] = useState<SelectProducto[]>([]);
  const [newOffset, setNewOffset] = useState<number | null>(0);
  const [totalProductos, setTotalProductos] = useState<number>(0);

  const fetchProductos = async () => {
    setLoading(true);
    const search = searchParams.get('q') ?? '';
    const offset = searchParams.get('offset') ?? '0';

    const params = new URLSearchParams({
      q: search,
      offset
    });

    const res = await fetch(`/api/producto?${params.toString()}`, {
      cache: 'no-store'
    });

    if (!res.ok) {
      setLoading(false);
      throw new Error('Error al obtener los productos');
    }

    const data = (await res.json()) as {
      productos: SelectProducto[];
      newOffset: number | null;
      totalProductos: number;
    };

    setProductos(data.productos);
    setNewOffset(data.newOffset);
    setTotalProductos(data.totalProductos);
    setLoading(false);
  };

  useEffect(() => {
    fetchProductos();
  }, [searchParams, reloadFlag]);

  return (
    <TabsContent value="all">
      <Productostable
        productos={productos}
        offset={newOffset ?? 0}
        totalProductos={totalProductos}
      />
    </TabsContent>
  );
}