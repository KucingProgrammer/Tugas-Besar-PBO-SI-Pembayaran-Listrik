import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface Transaksi {
    void lakukanTransaksi();
    double getHarga();
}

abstract class Akun implements Transaksi {
    protected String nama;
    protected double saldo;

    public Akun(String nama, double saldo) {
        this.nama = nama;
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public abstract void lakukanTransaksi();

    @Override
    public double getHarga() {
        return 0; // Default implementation untuk Transaksi yang tidak memiliki harga
    }
}

class Pelanggan extends Akun {
    private List<Transaksi> riwayatTransaksi;

    public Pelanggan(String nama, double saldo) {
        super(nama, saldo);
        this.riwayatTransaksi = new ArrayList<>();
    }

    public List<Transaksi> getRiwayatTransaksi() {
        return riwayatTransaksi;
    }

    @Override
    public void lakukanTransaksi() {
        Scanner scanner = new Scanner(System.in);
        int pilihan;

        do {
            System.out.println("\nHI " + nama + ", SALDO ANDA SAAT INI " + saldo + ", APA YANG INGIN ANDA LAKUKAN?");
            System.out.println("1. ISI SALDO");
            System.out.println("2. BELI PULSA");
            System.out.println("3. BELI TOKEN LISTRIK");
            System.out.println("4. RIWAYAT TRANSAKSI");
            System.out.print("Pilih opsi (1-4): ");
            pilihan = scanner.nextInt();

            switch (pilihan) {
                case 1:
                    isiSaldo();
                    break;
                case 2:
                    beliPulsa();
                    break;
                case 3:
                    beliTokenListrik();
                    break;
                case 4:
                    tampilkanRiwayatTransaksi();
                    break;
                default:
                    System.out.println("Opsi tidak valid. Silakan pilih opsi 1-4.");
            }
        } while (pilihan != 4);
    }

    private void isiSaldo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan jumlah saldo yang ingin diisi: ");
        double jumlahIsiSaldo = scanner.nextDouble();
        saldo += jumlahIsiSaldo;
        System.out.println("Saldo Anda telah diisi sebesar " + jumlahIsiSaldo + ". Saldo sekarang: " + saldo);
    }

    private void beliPulsa() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan jumlah pulsa yang ingin dibeli: ");
        double jumlahPulsa = scanner.nextDouble();
        if (saldo >= jumlahPulsa) {
            saldo -= jumlahPulsa;
            Pulsa pulsa = new Pulsa(jumlahPulsa);
            riwayatTransaksi.add(pulsa);
            System.out.println("Pulsa sebesar " + jumlahPulsa + " berhasil dibeli. Saldo sekarang: " + saldo);
        } else {
            System.out.println("Saldo tidak mencukupi untuk membeli pulsa sebesar " + jumlahPulsa);
        }
    }

    private void beliTokenListrik() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nDaftar Harga Token Listrik:");
        tampilkanDaftarHargaToken();
        System.out.print("\nMasukkan nomor token yang ingin dibeli: ");
        int nomorToken = scanner.nextInt();
        int nilaiToken = nomorToken * 25000;

        if (saldo >= nilaiToken) {
            saldo -= nilaiToken;
            TokenListrik tokenListrik = new TokenListrik(nilaiToken);
            riwayatTransaksi.add(tokenListrik);
            System.out.println("Token listrik sebesar " + nilaiToken + " berhasil dibeli. Saldo sekarang: " + saldo);
        } else {
            System.out.println("Saldo tidak mencukupi untuk membeli token listrik sebesar " + nilaiToken);
        }
    }

    private void tampilkanRiwayatTransaksi() {
        System.out.println("\nRiwayat Transaksi:");
        if (riwayatTransaksi.isEmpty()) {
            System.out.println("Belum ada transaksi.");
        } else {
            System.out.println("----------------------------------------------------");
            System.out.println("| NO | DESKRIPSI PEMBELIAN      | HARGA            |");
            System.out.println("----------------------------------------------------");
            for (int i = 0; i < riwayatTransaksi.size(); i++) {
                Transaksi transaksi = riwayatTransaksi.get(i);
                System.out.printf("| %-2d | %-25s | %-15.0f |\n", i + 1, transaksi.getClass().getSimpleName(),
                        transaksi.getHarga());
            }
            System.out.println("----------------------------------------------------");
        }
    }

    private static void tampilkanDaftarHargaToken() {
        System.out.println("----------------------------------------------------");
        System.out.println("| No | Nilai Token |");
        System.out.println("----------------------------------------------------");
        for (int i = 1; i <= 10; i++) {
            int nilaiToken = i * 25000;
            System.out.printf("| %-2d | %-12d |\n", i, nilaiToken);
        }
        System.out.println("----------------------------------------------------");
    }
}

class TokenListrik implements Transaksi {
    private double nilaiToken;

    public TokenListrik(double nilaiToken) {
        this.nilaiToken = nilaiToken;
    }

    @Override
    public void lakukanTransaksi() {
        System.out.println("Membeli token listrik dengan nilai: " + nilaiToken);
    }

    @Override
    public double getHarga() {
        return nilaiToken;
    }
}

class Pulsa implements Transaksi {
    private double nilaiPulsa;

    public Pulsa(double nilaiPulsa) {
        this.nilaiPulsa = nilaiPulsa;
    }

    @Override
    public void lakukanTransaksi() {
        System.out.println("Membeli pulsa dengan nilai: " + nilaiPulsa);
    }

    @Override
    public double getHarga() {
        return nilaiPulsa;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Meminta input nama pelanggan
        System.out.print("MASUKKAN NAMA ANDA: ");
        String namaPelanggan = scanner.nextLine();

        // Meminta input saldo awal
        System.out.print("MASUKKAN SALDO AWAL ANDA: ");
        double saldoAwal = scanner.nextDouble();

        // Membuat pelanggan
        Pelanggan pelanggan = new Pelanggan(namaPelanggan, saldoAwal);

        // Menampilkan halaman utama dan memulai interaksi
        pelanggan.lakukanTransaksi();
    }
}