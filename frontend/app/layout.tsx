import { Providers } from '@/components/providers';
import './globals.css';
import { Analytics } from '@vercel/analytics/react';

export const metadata = {
  title: 'Admin Dashboard de Gestion de creditos',
  description:
    'A user admin dashboard configured with Next.js, Postgres, NextAuth, Tailwind CSS, TypeScript, and Prettier.'
};

export default function RootLayout({
  children
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body className="flex min-h-screen w-full flex-col">
        <Providers>{children}</Providers>
      </body>
      <Analytics />
    </html>
  );
}
