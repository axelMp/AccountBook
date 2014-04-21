package accountbook

import grails.transaction.Transactional

import java.text.SimpleDateFormat;
import java.util.Date;

@Transactional
class UtilitiesService {
	private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    def parseDate(String aDate) {
		return formatter.parse(aDate);
    }
	
	def encodeDate(Date aDate) {
		return formatter.format(aDate);
	}
	
	def replaceHtmlEncodings(String aString) {
		log.info("replacing "+aString);
		log.info("by "+aString.replace("&amp;", "&").replace("&uuml;", "ü").replace("&ouml;", "ö").replace("&auml;", "ä"));
		return aString.replace("&amp;", "&").replace("&uuml;", "ü").replace("&ouml;", "ö").replace("&auml;", "ä");
	}
}
