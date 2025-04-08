/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  env: {
    // Variables de entorno que necesites disponibles en el cliente
    NEXT_PUBLIC_API_URL: process.env.NEXT_PUBLIC_API_URL,
  },
  images: {
    domains: ['cdn.pixabay.com'],
  },
  // Opcional: si trabajas con redirects
  async redirects() {
    return [
      {
        source: '/',
        destination: '/login',
        permanent: false,
      },
    ];
  },
};

module.exports = nextConfig;
