'use client';

import { useEffect } from 'react';

export default function Error({
  error,
  reset
}: {
  error: Error & { digest?: string };
  reset: () => void;
}) {
  useEffect(() => {
    // Log the error to an error reporting service
    console.error(error);
  }, [error]);

  return (
    <main className="p-4 md:p-6">
      <div className="mb-8 space-y-4">
        <h1 className="text-2xl font-semibold">Error</h1>
        <p>
          An error occurred. Please try again later. If the problem persists, contact support.
        </p>
        <p>
          <button
            className="text-secondary underline"
            onClick={() => {
              reset();
            }}
          >
            Reload
          </button>
        </p>
      </div>
    </main>
  );
}
