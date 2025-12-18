"use client"

import { useEffect, useMemo, useState } from "react"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Badge } from "@/components/ui/badge"
import { Button } from "@/components/ui/button"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import { ArrowDownCircle, ArrowUpCircle, Wallet, ChevronLeft, ChevronRight } from "lucide-react"
import { fetchTransactions, Transaction } from "@/lib/api"

export default function Dashboard() {
  const [accountNumber] = useState("ACC1234567890")

  const [transactions, setTransactions] = useState<Transaction[]>([])
  const [filterType, setFilterType] = useState<"ALL" | "CREDIT" | "DEBIT">("ALL")

  const [currentPage, setCurrentPage] = useState(0) // backend is 0-based
  const [totalPages, setTotalPages] = useState(0)

  const itemsPerPage = 10

  // Fetch transactions from backend
  useEffect(() => {
    fetchTransactions(accountNumber, currentPage, itemsPerPage)
      .then((data) => {
        setTransactions(data.content)
        setTotalPages(data.totalPages)
      })
      .catch(console.error)
  }, [accountNumber, currentPage])

  // Client-side filter (acceptable)
  const filteredTransactions = useMemo(() => {
    if (filterType === "ALL") return transactions
    return transactions.filter((t) => t.type === filterType)
  }, [transactions, filterType])

  // Summary metrics (computed from current page data)
  const summary = useMemo(() => {
    const totalCredits = filteredTransactions
      .filter((t) => t.type === "CREDIT")
      .reduce((sum, t) => sum + t.amount, 0)

    const totalDebits = filteredTransactions
      .filter((t) => t.type === "DEBIT")
      .reduce((sum, t) => sum + t.amount, 0)

    return {
      totalCredits,
      totalDebits,
      netBalance: totalCredits - totalDebits,
      currentBalance: filteredTransactions.at(-1)?.balanceAfter ?? 0,
    }
  }, [filteredTransactions])

  const formatCurrency = (amount: number) =>
    new Intl.NumberFormat("en-IN", {
      style: "currency",
      currency: "INR",
    }).format(amount)

  const formatDate = (timestamp: string) =>
    new Date(timestamp).toLocaleDateString("en-US", {
      month: "short",
      day: "numeric",
      year: "numeric",
      hour: "2-digit",
      minute: "2-digit",
    })

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-50 via-blue-50 to-purple-50">
      {/* Navbar */}
      <nav className="sticky top-0 z-50 border-b bg-white/80 backdrop-blur-sm">
        <div className="container mx-auto px-4 py-4">
          <h1 className="text-2xl font-bold bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent">
            FinBank
          </h1>
        </div>
      </nav>

      <main className="container mx-auto px-4 py-8 space-y-8">
        {/* Account Header */}
        <Card className="overflow-hidden border-0 shadow-lg">
          <div className="bg-gradient-to-r from-blue-600 via-blue-700 to-purple-600 p-8 text-white">
            <CardHeader className="p-0">
              <CardDescription className="text-blue-100">Account Number</CardDescription>
              <CardTitle className="text-3xl font-bold">{accountNumber}</CardTitle>
            </CardHeader>
            <div className="mt-6">
              <p className="text-sm text-blue-100">Current Balance</p>
              <p className="text-4xl font-bold mt-1">
                {formatCurrency(summary.currentBalance)}
              </p>
            </div>
          </div>
        </Card>

        {/* Summary Cards */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <Card className="border-0 shadow-md">
            <CardHeader className="pb-3 flex flex-row justify-between">
              <CardTitle className="text-sm text-muted-foreground">Total Credits</CardTitle>
              <ArrowUpCircle className="text-green-600" />
            </CardHeader>
            <CardContent>
              <p className="text-3xl font-bold text-green-600">
                {formatCurrency(summary.totalCredits)}
              </p>
            </CardContent>
          </Card>

          <Card className="border-0 shadow-md">
            <CardHeader className="pb-3 flex flex-row justify-between">
              <CardTitle className="text-sm text-muted-foreground">Total Debits</CardTitle>
              <ArrowDownCircle className="text-red-600" />
            </CardHeader>
            <CardContent>
              <p className="text-3xl font-bold text-red-600">
                {formatCurrency(summary.totalDebits)}
              </p>
            </CardContent>
          </Card>

          <Card className="border-0 shadow-md">
            <CardHeader className="pb-3 flex flex-row justify-between">
              <CardTitle className="text-sm text-muted-foreground">Net Balance</CardTitle>
              <Wallet className="text-blue-600" />
            </CardHeader>
            <CardContent>
              <p className="text-3xl font-bold text-blue-600">
                {formatCurrency(summary.netBalance)}
              </p>
            </CardContent>
          </Card>
        </div>

        {/* Transactions Table */}
        <Card className="border-0 shadow-lg">
          <CardHeader className="flex flex-row justify-between items-center">
            <div>
              <CardTitle>Transactions</CardTitle>
              <CardDescription>Paginated transaction history</CardDescription>
            </div>
            <Select value={filterType} onValueChange={(v: any) => setFilterType(v)}>
              <SelectTrigger className="w-[180px]">
                <SelectValue placeholder="Filter" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="ALL">All</SelectItem>
                <SelectItem value="CREDIT">Credit</SelectItem>
                <SelectItem value="DEBIT">Debit</SelectItem>
              </SelectContent>
            </Select>
          </CardHeader>

          <CardContent>
            {filteredTransactions.length === 0 ? (
              <div className="py-12 text-center text-muted-foreground">
                No transactions found
              </div>
            ) : (
              <>
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>Date</TableHead>
                      <TableHead>ID</TableHead>
                      <TableHead>Type</TableHead>
                      <TableHead className="text-right">Amount</TableHead>
                      <TableHead className="text-right">Balance After</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {filteredTransactions.map((t) => (
                      <TableRow key={t.id}>
                        <TableCell>{formatDate(t.timestamp)}</TableCell>
                        <TableCell>{t.id}</TableCell>
                        <TableCell>
                          <Badge
                            className={
                              t.type === "CREDIT"
                                ? "bg-green-600"
                                : "bg-red-600"
                            }
                          >
                            {t.type}
                          </Badge>
                        </TableCell>
                        <TableCell
                          className={`text-right font-semibold ${
                            t.type === "CREDIT" ? "text-green-600" : "text-red-600"
                          }`}
                        >
                          {formatCurrency(t.amount)}
                        </TableCell>
                        <TableCell className="text-right">
                          {formatCurrency(t.balanceAfter)}
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>

                {/* Pagination */}
                <div className="flex justify-between items-center mt-6">
                  <span className="text-sm text-muted-foreground">
                    Page {currentPage + 1} of {totalPages}
                  </span>
                  <div className="flex gap-2">
                    <Button
                      variant="outline"
                      size="sm"
                      disabled={currentPage === 0}
                      onClick={() => setCurrentPage((p) => p - 1)}
                    >
                      <ChevronLeft className="h-4 w-4 mr-1" />
                      Prev
                    </Button>
                    <Button
                      variant="outline"
                      size="sm"
                      disabled={currentPage === totalPages - 1}
                      onClick={() => setCurrentPage((p) => p + 1)}
                    >
                      Next
                      <ChevronRight className="h-4 w-4 ml-1" />
                    </Button>
                  </div>
                </div>
              </>
            )}
          </CardContent>
        </Card>
      </main>
    </div>
  )
}
