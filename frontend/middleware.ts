import { NextResponse } from 'next/server';
import type { NextRequest } from 'next/server';

const PUBLIC_ROUTES = ['/login', '/register'];

export async function middleware(req: NextRequest) {
  const { pathname } = req.nextUrl;

  console.log('Pathname:', pathname);

  // Ignorar recursos internos y estáticos
  if (
    pathname.startsWith('/_next') ||
    pathname.startsWith('/static') ||
    pathname.startsWith('/favicon.ico') ||
    pathname.includes('.')
  ) {
    return NextResponse.next();
  }

  // Permitir rutas públicas
  if (PUBLIC_ROUTES.includes(pathname)) {
    return NextResponse.next();
  }

  // Proteger rutas de la API verificando el token en las cookies
  if (pathname.startsWith('/api')) {
    const token = req.cookies.get('token')?.value;

    if (!token) {
      return NextResponse.json({ message: 'No autorizado' }, { status: 401 });
    }

    return NextResponse.next();
  }

  // Proteger páginas redirigiendo a /login si no hay token
  const token = req.cookies.get('token')?.value;

  if (!token) {
    const loginUrl = new URL('/login', req.url);
    return NextResponse.redirect(loginUrl);
  }

  return NextResponse.next();
}

export const config = {
  matcher: ['/((?!_next|static|favicon.ico|.*\\..*).*)'],
};