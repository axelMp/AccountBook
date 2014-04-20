package accountbook

import grails.transaction.Transactional

import java.text.SimpleDateFormat;
import java.util.Date;

@Transactional
class UtilitiesService {
	private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    def parseDate(String aDate) {
		return formatter.parse(occurredOn);
    }
	
	def encodeDate(Date aDate) {
		return formatter.format(aDate);
	}
}
