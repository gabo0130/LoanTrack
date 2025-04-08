'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { Button } from '@/components/ui/button';
import { LogInIcon, Eye, EyeOff } from 'lucide-react';

export default function LoginPage() {
  const router = useRouter();
  const [form, setForm] = useState({ username: '', password: '' });
  const [error, setError] = useState('');
  const [passwordShown, setPasswordShown] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    const res = await fetch('/api/login', {
      method: 'POST',
      body: JSON.stringify(form),
      headers: { 'Content-Type': 'application/json' }
    });

    if (res.ok) {
      router.push('/dashboard');
    } else {
      const data = await res.json();
      setError(data.error || 'Error');
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <div className="m-3 mb-5 py-4 bg-white shadow rounded w-full max-w-sm">
        <div className="text-center">
          <p className="text-2xl font-bold">Gestion de creditos</p>
        </div>
        <div className="text-center mt-2">
          
        </div>
        <div className="text-center mb-2">
          <p className="text-xl font-bold">Inicio de Sesión</p>
        </div>
        <form onSubmit={handleSubmit} className="flex flex-col gap-3 p-4">
          <input
            type="text"
            placeholder="Username"
            value={form.username}
            onChange={(e) => setForm({ ...form, username: e.target.value })}
            className="border p-2 rounded"
          />
          <div className="relative">
            <input
              type={passwordShown ? 'text' : 'password'}
              placeholder="Password"
              value={form.password}
              onChange={(e) => setForm({ ...form, password: e.target.value })}
              className="border p-2 rounded w-full"
            />
            <button
              type="button"
              onClick={() => setPasswordShown(!passwordShown)}
              className="absolute inset-y-0 right-0 flex items-center pr-3"
            >
              {passwordShown ? <EyeOff className="h-5 w-5" /> : <Eye className="h-5 w-5" />}
            </button>
          </div>
          {error && <p className="text-red-500">{error}</p>}
          <Button type="submit" className="h-8 gap-1">
            <LogInIcon className="h-3.5 w-3.5" />
            <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
              Entrar
            </span>
          </Button>
        </form>
      </div>
    </div>
  );
}