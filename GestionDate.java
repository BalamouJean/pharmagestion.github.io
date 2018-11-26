package com.starupFX.startup;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

public class GestionDate 
{
	public java.sql.Date curentDate()
	{
		LocalDate localDate= LocalDate.now(ZoneId.of("Africa/Conakry"));
		return java.sql.Date.valueOf(localDate);
	}
	public Period compareDate(LocalDate oldDate,LocalDate newDate)
	{
		Period diff= Period.between(oldDate, newDate);
		return diff;
	}
}
