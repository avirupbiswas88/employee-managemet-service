package com.employee.util;

/**
 * Enumeration representing country-specific tax deduction strategies
 * used for employee salary calculations.
 *
 * <p>This enum implements the Strategy Design Pattern where each country
 * defines its own tax deduction logic applied to an employee's gross salary.
 *
 * <p>The strategy is used during employee salary processing to determine:
 * <ul>
 *     <li>Tax deduction amount</li>
 *     <li>Net salary after deduction</li>
 * </ul>
 *
 * <p>Supported countries:
 * <ul>
 *     <li>USA</li>
 *     <li>INDIA</li>
 *     <li>OTHERS</li>
 * </ul>
 *
 * <p>Usage Example:
 * <pre>
 * CountryTaxStrategy strategy = CountryTaxStrategy.fromCountry("USA");
 * double deduction = strategy.calculateDeduction(grossSalary);
 * </pre>
 *
 * <p>This allows flexible extension of country-specific tax rules without
 * modifying core salary calculation logic.
 *
 * @author Avirup Biswas
 * Employee Management System
 */
public enum CountryTaxStrategy {
    /**
     * Tax strategy for the India.
     * Applies a 10% tax deduction on gross salary.
     */
    INDIA {
        @Override
        public double calculateDeduction(double grossSalary) {
            return grossSalary * 0.10;
        }
    },

    /**
     * Tax strategy for the United States.
     * Applies a 12% tax deduction on gross salary.
     */
    UNITED_STATES {
        @Override
        public double calculateDeduction(double grossSalary) {
            return grossSalary * 0.12;
        }
    },

    /**
     * Tax strategy for the Other Countries.
     * Applies a 0% tax deduction on gross salary.
     */
    OTHER {
        @Override
        public double calculateDeduction(double grossSalary) {
            return 0;
        }
    };

    /**
     * Calculates the tax deduction based on the country-specific
     * tax rule applied to the provided gross salary.
     *
     * @param grossSalary the employee's gross salary
     * @return calculated deduction amount based on country tax rule
     * @author Avirup Biswas
     */
    public abstract double calculateDeduction(double grossSalary);

    /**
     * Retrieves the tax strategy corresponding to the provided country.
     *
     * <p>This method performs a lookup to determine which tax
     * deduction strategy should be applied for the given country.
     *
     * @param country the country name
     * @return {@link CountryTaxStrategy} representing the country's tax rule
     * @author Avirup Biswas
     */
    public static CountryTaxStrategy fromCountry(String country) {

        switch (country.toLowerCase()) {
            case "india":
                return INDIA;
            case "united states":
            case "usa":
                return UNITED_STATES;
            default:
                return OTHER;
        }
    }
}
