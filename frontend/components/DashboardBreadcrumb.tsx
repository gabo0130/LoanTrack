'use client';

import Link from 'next/link';
import { usePathname } from 'next/navigation';
import { useEffect, useState } from 'react';
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator
} from '@/components/ui/breadcrumb';

export default function DashboardBreadcrumb() {
  const pathname = usePathname();
  const [breadcrumbItems, setBreadcrumbItems] = useState<{ href: string; label: string }[]>([]);

  useEffect(() => {
    const pathSegments = pathname.split('/').filter((segment) => segment);
    const items = pathSegments.map((segment, index) => {
      const href = '/' + pathSegments.slice(0, index + 1).join('/');
      return { href, label: segment.charAt(0).toUpperCase() + segment.slice(1) };
    });
    setBreadcrumbItems([{ href: '/dashboard', label: '' }, ...items]);
  }, [pathname]);

  return (
    <Breadcrumb className="hidden md:flex">
      <BreadcrumbList>
        {breadcrumbItems.map((item, index) => (
          <BreadcrumbItem key={index}>
            {index < breadcrumbItems.length - 1 ? (
              <BreadcrumbLink asChild>
                <Link href={item.href}>{item.label}</Link>
              </BreadcrumbLink>
            ) : (
              <BreadcrumbPage>{item.label}</BreadcrumbPage>
            )}
            {index < breadcrumbItems.length - 1 && <BreadcrumbSeparator />}
          </BreadcrumbItem>
        ))}
      </BreadcrumbList>
    </Breadcrumb>
  );
}
