const BASE_URL = "http://localhost:8080";

export type Transaction = {
  id: string;
  accountNumber: string;
  amount: number;
  balanceAfter: number;
  type: "CREDIT" | "DEBIT";
  timestamp: string;
};

export type TransactionPage = {
  content: Transaction[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
};

export async function fetchTransactions(
  accountNumber: string,
  page: number,
  size: number
): Promise<TransactionPage> {
  const res = await fetch(
    `${BASE_URL}/accounts/${accountNumber}/transactions?page=${page}&size=${size}`
  );

  if (!res.ok) {
    throw new Error("Failed to fetch transactions");
  }

  return res.json();
}
