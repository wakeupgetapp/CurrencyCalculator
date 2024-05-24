package com.wakeupgetapp.core.database.internal.util

internal val currencyInfoMap: Map<String, Pair<String, String>> = mapOf(

    "USD" to Pair("US Dollar", "$"),
    "CAD" to Pair("Canadian Dollar", "CA$"),
    "EUR" to Pair("Euro", "€"),
    "AED" to Pair("United Arab Emirates Dirham", "AED"),
    "AFN" to Pair("Afghan Afghani", "Af"),
    "ALL" to Pair("Albanian Lek", "ALL"),
    "AMD" to Pair("Armenian Dram", "AMD"),
    "ARS" to Pair("Argentine Peso", "AR$"),
    "AUD" to Pair("Australian Dollar", "AU$"),
    "AZN" to Pair("Azerbaijani Manat", "man."),
    "BAM" to Pair("Bosnia-Herzegovina Convertible Mark", "KM"),
    "BDT" to Pair("Bangladeshi Taka", "Tk"),
    "BGN" to Pair("Bulgarian Lev", "BGN"),
    "BHD" to Pair("Bahraini Dinar", "BD"),
    "BIF" to Pair("Burundian Franc", "FBu"),
    "BND" to Pair("Brunei Dollar", "BN$"),
    "BOB" to Pair("Bolivian Boliviano", "Bs"),
    "BRL" to Pair("Brazilian Real", "R$"),
    "BWP" to Pair("Botswanan Pula", "BWP"),
    "BYN" to Pair("Belarusian Ruble", "Br"),
    "BZD" to Pair("Belize Dollar", "BZ$"),
    "CDF" to Pair("Congolese Franc", "CDF"),
    "CHF" to Pair("Swiss Franc", "CHF"),
    "CLP" to Pair("Chilean Peso", "CL$"),
    "CNY" to Pair("Chinese Yuan", "CN¥"),
    "COP" to Pair("Colombian Peso", "CO$"),
    "CRC" to Pair("Costa Rican Colón", "₡"),
    "CVE" to Pair("Cape Verdean Escudo", "CV$"),
    "CZK" to Pair("Czech Republic Koruna", "Kč"),
    "DJF" to Pair("Djiboutian Franc", "Fdj"),
    "DKK" to Pair("Danish Krone", "Dkr"),
    "DOP" to Pair("Dominican Peso", "RD$"),
    "DZD" to Pair("Algerian Dinar", "DA"),
    "EEK" to Pair("Estonian Kroon", "Ekr"),
    "EGP" to Pair("Egyptian Pound", "EGP"),
    "ERN" to Pair("Eritrean Nakfa", "Nfk"),
    "ETB" to Pair("Ethiopian Birr", "Br"),
    "GBP" to Pair("British Pound Sterling", "£"),
    "GEL" to Pair("Georgian Lari", "GEL"),
    "GHS" to Pair("Ghanaian Cedi", "GH₵"),
    "GNF" to Pair("Guinean Franc", "FG"),
    "GTQ" to Pair("Guatemalan Quetzal", "GTQ"),
    "HKD" to Pair("Hong Kong Dollar", "HK$"),
    "HNL" to Pair("Honduran Lempira", "HNL"),
    "HRK" to Pair("Croatian Kuna", "kn"),
    "HUF" to Pair("Hungarian Forint", "Ft"),
    "IDR" to Pair("Indonesian Rupiah", "Rp"),
    "ILS" to Pair("Israeli New Sheqel", "₪"),
    "INR" to Pair("Indian Rupee", "Rs"),
    "IQD" to Pair("Iraqi Dinar", "IQD"),
    "IRR" to Pair("Iranian Rial", "IRR"),
    "ISK" to Pair("Icelandic Króna", "Ikr"),
    "JMD" to Pair("Jamaican Dollar", "J$"),
    "JOD" to Pair("Jordanian Dinar", "JD"),
    "JPY" to Pair("Japanese Yen", "¥"),
    "KES" to Pair("Kenyan Shilling", "Ksh"),
    "KHR" to Pair("Cambodian Riel", "KHR"),
    "KMF" to Pair("Comorian Franc", "CF"),
    "KRW" to Pair("South Korean Won", "₩"),
    "KWD" to Pair("Kuwaiti Dinar", "KD"),
    "KZT" to Pair("Kazakhstani Tenge", "KZT"),
    "LBP" to Pair("Lebanese Pound", "LB£"),
    "LKR" to Pair("Sri Lankan Rupee", "SLRs"),
    "LTL" to Pair("Lithuanian Litas", "Lt"),
    "LVL" to Pair("Latvian Lats", "Ls"),
    "LYD" to Pair("Libyan Dinar", "LD"),
    "MAD" to Pair("Moroccan Dirham", "MAD"),
    "MDL" to Pair("Moldovan Leu", "MDL"),
    "MGA" to Pair("Malagasy Ariary", "MGA"),
    "MKD" to Pair("Macedonian Denar", "MKD"),
    "MMK" to Pair("Myanma Kyat", "MMK"),
    "MOP" to Pair("Macanese Pataca", "MOP$"),
    "MUR" to Pair("Mauritian Rupee", "MURs"),
    "MXN" to Pair("Mexican Peso", "MX$"),
    "MYR" to Pair("Malaysian Ringgit", "RM"),
    "MZN" to Pair("Mozambican Metical", "MTn"),
    "NAD" to Pair("Namibian Dollar", "N$"),
    "NGN" to Pair("Nigerian Naira", "₦"),
    "NIO" to Pair("Nicaraguan Córdoba", "C$"),
    "NOK" to Pair("Norwegian Krone", "Nkr"),
    "NPR" to Pair("Nepalese Rupee", "NPRs"),
    "NZD" to Pair("New Zealand Dollar", "NZ$"),
    "OMR" to Pair("Omani Rial", "OMR"),
    "PAB" to Pair("Panamanian Balboa", "B/."),
    "PEN" to Pair("Peruvian Nuevo Sol", "S/."),
    "PHP" to Pair("Philippine Peso", "₱"),
    "PKR" to Pair("Pakistani Rupee", "PKRs"),
    "PLN" to Pair("Polish Zloty", "zł"),
    "PYG" to Pair("Paraguayan Guarani", "₲"),
    "QAR" to Pair("Qatari Rial", "QR"),
    "RON" to Pair("Romanian Leu", "RON"),
    "RSD" to Pair("Serbian Dinar", "din."),
    "RUB" to Pair("Russian Ruble", "RUB"),
    "RWF" to Pair("Rwandan Franc", "RWF"),
    "SAR" to Pair("Saudi Riyal", "SR"),
    "SDG" to Pair("Sudanese Pound", "SDG"),
    "SEK" to Pair("Swedish Krona", "Skr"),
    "SGD" to Pair("Singapore Dollar", "S$"),
    "SOS" to Pair("Somali Shilling", "Ssh"),
    "SYP" to Pair("Syrian Pound", "SY£"),
    "THB" to Pair("Thai Baht", "฿"),
    "TND" to Pair("Tunisian Dinar", "DT"),
    "TOP" to Pair("Tongan Paʻanga", "T$"),
    "TRY" to Pair("Turkish Lira", "TL"),
    "TTD" to Pair("Trinidad and Tobago Dollar", "TT$"),
    "TWD" to Pair("New Taiwan Dollar", "NT$"),
    "TZS" to Pair("Tanzanian Shilling", "TSh"),
    "UAH" to Pair("Ukrainian Hryvnia", "₴"),
    "UGX" to Pair("Ugandan Shilling", "USh"),
    "UYU" to Pair("Uruguayan Peso", "U"),
    "UZS" to Pair("Uzbekistan Som", "UZS"),
    "VEF" to Pair("Venezuelan Bolívar", "Bs.F."),
    "VND" to Pair("Vietnamese Dong", "₫"),
    "XAF" to Pair("CFA Franc BEAC", "FCFA"),
    "XOF" to Pair("CFA Franc BCEAO", "CFA"),
    "YER" to Pair("Yemeni Rial", "YR"),
    "ZAR" to Pair("South African Rand", "R"),
    "ZMK" to Pair("Zambian Kwacha", "ZK"),
    "ZWL" to Pair("Zimbabwean Dollar", "ZWL$")
)