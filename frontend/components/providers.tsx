"use client";


import { LoadingProvider } from "@/components/LoadingProvider";
import { GlobalLoading } from "@/components/GlobalLoading";
import { AlertProvider } from "@/components/AlertContext";

export function Providers({ children }: { children: React.ReactNode }) {
  return (
    <AlertProvider>
      <LoadingProvider>
        <GlobalLoading />
        {children}
      </LoadingProvider>
    </AlertProvider>
  );
}
