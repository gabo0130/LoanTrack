import { NextResponse } from 'next/server';
import { ProductoService } from '@/services/productoService';
import { SelectProducto } from '@/models/producto';


const productoService = new ProductoService();


export async function GET(req: Request) {
  const { searchParams } = new URL(req.url);

  const search = searchParams.get('q') ?? '';
  const offset = searchParams.get('offset') ?? 0;

  const productoService = new ProductoService();
  const { productos, newOffset, totalProductos } = await productoService.getProductosWithPagination(
    search,
    Number(offset)
  ) as { productos: SelectProducto[], newOffset: number | null, totalProductos: number };

  return NextResponse.json({ productos, newOffset, totalProductos });
}


export async function POST(req: Request) {
  const body = await req.json();
  console.log('Body recibido:', body);
  const producto = await productoService.createProducto(body);

  return NextResponse.json(producto);
}
