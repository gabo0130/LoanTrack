// dashboard/components/ReportCharts.tsx

"use client";

import React from "react";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  PointElement,
  LineElement,
} from "chart.js";
import { Bar, Pie, Line } from "react-chartjs-2";

// Registro de los componentes necesarios para los distintos tipos de gráficos
ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  PointElement,
  LineElement
);

// Datos simulados de cuotas vencidas o del día
const pagosPendientes = [
  { cliente: "Juan Pérez", monto: "$150.000", fecha: "2025-03-30", estado: "Por cobrar hoy" },
  { cliente: "Ana Gómez", monto: "$120.000", fecha: "2025-03-30", estado: "Vencida" },
  { cliente: "Carlos Ruiz", monto: "$200.000", fecha: "2025-03-29", estado: "Vencida" },
];
const getLastWeeks = (numWeeks: number) => {
    const weeks = [];
    const today = new Date();
    for (let i = 0; i < numWeeks; i++) {
      const startOfWeek = new Date(today);
      startOfWeek.setDate(today.getDate() - today.getDay() - i * 7); // Lunes de la semana
      const endOfWeek = new Date(startOfWeek);
      endOfWeek.setDate(startOfWeek.getDate() + 6); // Domingo de la semana
  
      const formatDateWithoutYear = (date: Date) =>
        date.toLocaleDateString("es-ES", { day: "2-digit", month: "2-digit" });
  
      weeks.push(
        `${formatDateWithoutYear(startOfWeek)} - ${formatDateWithoutYear(endOfWeek)}`
      );
    }
    return weeks.reverse();
  };
export const ReportCharts = () => {
  const lastWeeks = getLastWeeks(4);
  const creditosEstadoData = {
    labels: ["Abiertos", "Pagados", "Vencidos"],
    datasets: [
      {
        label: "Créditos",
        data: [15, 10, 3],
        backgroundColor: ["#3b82f6", "#10b981", "#ef4444"],
      },
    ],
  };

  const ingresosProyectados = {
    labels: lastWeeks,
    datasets: [
      {
        label: "Proyectado",
        data: [500000, 450000, 600000, 650000],
        borderColor: "#3b82f6",
        backgroundColor: "#3b82f666",
      },
      {
        label: "Recaudado",
        data: [480000, 430000, 550000, 620000],
        borderColor: "#10b981",
        backgroundColor: "#10b98166",
      },
    ],
  };

  return (
    <div className="p-4 space-y-8">
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        <div className="bg-white p-4 rounded-xl shadow-md">
          <h3 className="text-base font-semibold mb-2">Estado de los Créditos</h3>
          <Pie data={creditosEstadoData} />
        </div>
        <div className="bg-white p-4 rounded-xl shadow-md col-span-1 sm:col-span-2 lg:col-span-3">
          <h3 className="text-base font-semibold mb-2">Ingresos Proyectados vs. Recaudados</h3>
          <Line data={ingresosProyectados} />
        </div>
      </div>
    </div>
  );
};
