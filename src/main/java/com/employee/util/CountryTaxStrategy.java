package com.employee.util;

public enum CountryTaxStrategy {
    INDIA {
        @Override
        public double calculateDeduction(double grossSalary) {
            return grossSalary * 0.10;
        }
    },

    UNITED_STATES {
        @Override
        public double calculateDeduction(double grossSalary) {
            return grossSalary * 0.12;
        }
    },

    OTHER {
        @Override
        public double calculateDeduction(double grossSalary) {
            return 0;
        }
    };
    public abstract double calculateDeduction(double grossSalary);

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
