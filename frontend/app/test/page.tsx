'use client';

import { useState } from 'react';

export default function TestPage() {
  const [message, setMessage] = useState('');

  const handleClick = () => {
    console.log('Button clicked!');
    setMessage('¡Botón fue presionado!');
  };

  return (
    <div style={{ textAlign: 'center', marginTop: '50px' }}>
      <h1>Prueba básica</h1>
      <button
        onClick={handleClick}
        style={{ padding: '10px 20px', backgroundColor: 'blue', color: 'white' }}
      >
        Presionar
      </button>
      {message && <p>{message}</p>}
    </div>
  );
}
