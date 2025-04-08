export default async function Page({
    params,
  }: {
    params: Promise<{ paymentId: string }>
  }) {
    const { paymentId } = await params
    return <div>My paymentId: {paymentId}</div>
  }