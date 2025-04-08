"use client";

import { useLoading } from "./LoadingProvider";
import { Loader2 } from "lucide-react";

export const GlobalLoading = () => {
  const { isLoading } = useLoading();

  if (!isLoading) return null;

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50">
      <Loader2 className="h-12 w-12 animate-spin text-white" />
    </div>
  );
};
