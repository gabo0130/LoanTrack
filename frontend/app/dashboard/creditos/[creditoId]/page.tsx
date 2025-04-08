export default async function Page({
    params,
  }: {
    params: Promise<{ creditoId: string }>
  }) {
    const { creditoId } = await params
    return <div>My credit: {creditoId}</div>
  }