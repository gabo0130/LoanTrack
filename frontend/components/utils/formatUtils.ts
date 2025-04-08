
export function formatMoney(value: number | string | null): string {
    if (value === null) return '$0';
    const formatter = new Intl.NumberFormat('es-CO', {
        style: 'currency',
        currency: 'COP',
        minimumFractionDigits: 0
    });
    return formatter.format(Number(value));
}

export function formatDate(date: Date): string {
    if (!date) return '';
    return date.toISOString().split('T')[0];
}