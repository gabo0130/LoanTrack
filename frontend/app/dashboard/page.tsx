import { ReportCharts } from "@/components/ReportCharts";

export default function DashboardPage() {
  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-4">Resumen General</h1>
      <ReportCharts />
    </div>
  );
}
