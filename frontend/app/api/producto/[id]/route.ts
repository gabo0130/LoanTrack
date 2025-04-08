import { NextResponse } from 'next/server';
import { ProductoService } from '@/services/productoService';

const productoService = new ProductoService();

export async function GET(req: Request, { params }: { params: Promise<{ id: string }> }) {
  const { id } = await params;
  const producto = await productoService.getProductoWithDetails(Number(id));
  
  if (!producto) {
    return NextResponse.json({ message: 'Producto no encontrado' }, { status: 404 });
  }
  
  return NextResponse.json(producto);
}

export async function PUT(req: Request, { params }: { params: Promise<{ id: string }> }) {
  const { id
   } = await params;
  const body = await req.json();

  console.log('Body:', body);
  const productoActualizado = await productoService.updateProducto(Number(id), body);
  
  return NextResponse.json(productoActualizado);
}
