package lib;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

public class Employee {

	private String employeeId;
	private String firstName;
	private String lastName;
	private String idNumber;
	private String address;
	
	private int yearJoined;
	private int monthJoined;
	private int dayJoined;
	private int monthWorkingInYear;
	
	private boolean isForeigner;
	private boolean gender; //true = Laki-laki, false = Perempuan
	
	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;
	
	private String spouseName;
	private String spouseIdNumber;

	private List<String> childNames;
	private List<String> childIdNumbers;
	
	public Employee(String employeeId, String firstName, String lastName, String idNumber, String address, int yearJoined, int monthJoined, int dayJoined, boolean isForeigner, boolean gender) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.idNumber = idNumber;
		this.address = address;
		this.yearJoined = yearJoined;
		this.monthJoined = monthJoined;
		this.dayJoined = dayJoined;
		this.isForeigner = isForeigner;
		this.gender = gender;
		
		childNames = new LinkedList<String>();
		childIdNumbers = new LinkedList<String>();
	}
	
	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3: 7.000.000 per bulan)
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 */
	
	 public enum Grade {
        GRADE_1(3000000),
        GRADE_2(5000000),
        GRADE_3(7000000);

        private final int monthlySalary;

        Grade(int monthlySalary) {
            this.monthlySalary = monthlySalary;
        }

        public int getMonthlySalary() {
            return monthlySalary;
        }
    }

	private int calculateAdjustedSalary(int baseSalary) {
        return isForeigner ? (int) (baseSalary * 1.5) : baseSalary;
    }

    public void setMonthlySalary(Grade grade) {
        monthlySalary = calculateAdjustedSalary(grade.getMonthlySalary());
    }

	//PERUBAHAN PADA setMonthlySalary karena terlalu rumit untuk dibaca maka saya singkat dan 
	//saya tambahkah calculatedAdjustedSalary untuk menghitung Salarynya
	
	public void setAnnualDeductible(int deductible) {	
		this.annualDeductible = deductible;
	}
	
	public void setAdditionalIncome(int income) {	
		this.otherMonthlyIncome = income;
	}
	
	public void setSpouse(String spouseName, String spouseIdNumber) {
		this.spouseName = spouseName;
		this.spouseIdNumber = idNumber;
	}
	
	public void addChild(String childName, String childIdNumber) {
		childNames.add(childName);
		childIdNumbers.add(childIdNumber);
	}
	
	//Menghitung berapa lama pegawai bekerja dalam setahun ini, jika pegawai sudah bekerja dari tahun sebelumnya maka otomatis dianggap 12 bulan.
	
	private int calculateMonthsWorkedInYear(LocalDate currentDate) {
        return currentDate.getYear() == yearJoined ? currentDate.getMonthValue() - monthJoined : 12;
    }

	public int getAnnualIncomeTax() {
        LocalDate currentDate = LocalDate.now();
        int monthsWorkedInYear = calculateMonthsWorkedInYear(currentDate);
        return TaxFunction.calculateTax(monthlySalary, otherMonthlyIncome, monthsWorkedInYear, annualDeductible, isSpouseIdEmpty(), childIdNumbers.size());
    }

}
