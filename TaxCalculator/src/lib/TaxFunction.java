package lib;

public class TaxFunction {
	/**
	 * Fungsi untuk menghitung jumlah pajak penghasilan pegawai yang harus dibayarkan setahun.
	 * 
	 * Pajak dihitung sebagai 5% dari penghasilan bersih tahunan (gaji dan pemasukan bulanan lainnya dikalikan jumlah bulan bekerja dikurangi pemotongan) dikurangi penghasilan tidak kena pajak.
	 * 
	 * Jika pegawai belum menikah dan belum punya anak maka penghasilan tidak kena pajaknya adalah Rp 54.000.000.
	 * Jika pegawai sudah menikah maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000.
	 * Jika pegawai sudah memiliki anak maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000 per anak sampai anak ketiga.
	 * 
	 */

	// Menggunakan konstanta untuk nilai-nilai tetap
	private static final int TAX_RATE_PERCENTAGE = 5;
	private static final int SINGLE_TAX_FREE_INCOME = 54000000;
	private static final int MARRIED_TAX_FREE_INCOME = 58500000;
	private static final int CHILD_TAX_FREE_INCOME_PER_CHILD = 1500000;
	private static final int MAX_CHILDREN_FOR_TAX_FREE_INCOME = 3;

	public static int calculateTax(int monthlySalary, int otherMonthlyIncome, int numberOfMonthsWorked, int deductible, boolean isMarried, int numberOfChildren) {

		// Validasi jumlah bulan bekerja
		if (numberOfMonthsWorked > 12) {
			System.err.println("More than 12 months worked per year");
			return 0; // Mengembalikan 0 karena data tidak valid
		}

		// Mengatur jumlah anak agar tidak melebihi batas maksimum
		numberOfChildren = Math.min(numberOfChildren, MAX_CHILDREN_FOR_TAX_FREE_INCOME);

		// Menghitung pendapatan tidak kena pajak berdasarkan status perkawinan dan jumlah anak
		int taxFreeIncome;
		if (isMarried) {
			taxFreeIncome = MARRIED_TAX_FREE_INCOME + (numberOfChildren * CHILD_TAX_FREE_INCOME_PER_CHILD);
		} else {
			taxFreeIncome = SINGLE_TAX_FREE_INCOME + (numberOfChildren * CHILD_TAX_FREE_INCOME_PER_CHILD);
		}

		// Menghitung jumlah pajak
		int taxableIncome = ((monthlySalary + otherMonthlyIncome) * numberOfMonthsWorked) - deductible - taxFreeIncome;
		int tax = (int) Math.round(TAX_RATE_PERCENTAGE * taxableIncome / 100);

		// Mengembalikan 0 jika pajak negatif
		return Math.max(tax, 0);
	}
}

