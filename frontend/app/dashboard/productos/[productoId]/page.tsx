export default async function Page({
  params,
}: {
  params: Promise<{ productoId: string }>
}) {
  const { productoId } = await params
  return <div>Mi productoId: {productoId}</div>
}