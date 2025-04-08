'use client';
import React, {useState, Suspense } from 'react';
import { Tabs, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { File } from 'lucide-react';
import { Button } from '@/components/ui/button';
import ModalAddProducto from './ProductoForm';
import ProductosContent from './ProductosContent';

export default function ProductosPage() {
  const [reloadFlag, setReloadFlag] = useState(false);

  const reloadProductos = () => {
    setReloadFlag(prev => !prev);
  };
  return (
    <Suspense fallback={<div>Cargando productos...</div>}>
      <Tabs defaultValue="all">
        <div className="flex items-center">
          <TabsList>
            <TabsTrigger value="all">All</TabsTrigger>
            <TabsTrigger value="active">Activo</TabsTrigger>
            <TabsTrigger value="draft">Draft</TabsTrigger>
            <TabsTrigger value="archived" className="hidden sm:flex">
              Archived
            </TabsTrigger>
          </TabsList>
          <div className="ml-auto flex items-center gap-2">
            <Button size="sm" variant="outline" className="h-8 gap-1">
              <File className="h-3.5 w-3.5" />
              <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
                Export
              </span>
            </Button>
            <ModalAddProducto reloadProductos={reloadProductos} />
          </div>
        </div>
        <ProductosContent reloadFlag={reloadFlag} />
      </Tabs>
    </Suspense>
  );
}