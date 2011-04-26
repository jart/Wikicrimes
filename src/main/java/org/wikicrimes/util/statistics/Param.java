package org.wikicrimes.util.statistics;

import java.awt.Rectangle;
import java.security.InvalidParameterException;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.wikicrimes.util.kernelmap.LatLngBoundsGM;

public class Param {

	public static class Keys{
		
		private static final String application = "app";
		private static final String action = "action";
		private static final String zoom = "zoom";
		
		private static class PixelBounds{
			private static final String north = "northPixel";
//			private static final String south = "southPixel";
//			private static final String east = "eastPixel";
			private static final String west = "westPixel";
			private static final String width = "widthPixel";
			private static final String height = "heightPixel";
		}

		private static class LatLngBounds{
			private static final String north = "northLatLng";
			private static final String south = "southLatLng";
			private static final String east = "eastLatLng";
			private static final String west = "westLatLng";
			private static final String width = "widthLatLng";
			private static final String height = "heightLatLng";
		}
		
	}
	
	public static enum Application{
		WIKICRIMES, WIKIMAPPS;
	}
	
	public static enum Action{
		FULL_STATISTICS, KERNEL_MAP_ONLY, GET_IMAGE, GET_JSON, EVENTS;
	}
	
	public static Application getApplication(HttpServletRequest request) {
		String app = request.getParameter(Keys.application);
		if(app == null)
			return Application.WIKICRIMES; //default
		if(app.equals("wikicrimes"))
			return Application.WIKICRIMES;
		else if(app.equals("wikimapps"))
			return Application.WIKIMAPPS;
		else
			throw new InvalidParameterException(app);
	}
	
	public static Action getAction(ServletRequest request) {
		String app = request.getParameter(Keys.action);
		if(app.equals("full"))
			return Action.FULL_STATISTICS;
		else if(app.equals("kernel"))
			return Action.KERNEL_MAP_ONLY;
		else if(app.equals("getJson"))
			return Action.GET_JSON;
		else if(app.equals("getImage"))
			return Action.GET_IMAGE;
		else if(app.equals("events"))
			return Action.EVENTS;
		else
			throw new InvalidParameterException(app);
	}

	
	/**
	 * obs: O zoom do GoogleMaps varia de 0 a 19. Zero eh o mais de longe. 
	 */
	public static int getZoom(ServletRequest request){
		String zoomStr = request.getParameter(Keys.zoom);
		try{
			return Integer.parseInt(zoomStr);
		}catch(NumberFormatException e){
			throw new InvalidParameterException(zoomStr);
		}
	}
	
	public static Rectangle getPixelBounds(ServletRequest request){
		//obs: width = east-west nao funciona no caso em q a emenda do mapa esta aparecendo na tela (a linha entre Japao e EUA) 
		//width seria negativo ja q west>east
		//por isso o width e o height estao sendo calculado no javascript, usando as coordenadas do centro da tela
		String northStr = request.getParameter(Keys.PixelBounds.north);
//		String southStr = request.getParameter("southPixel");
//		String eastStr = request.getParameter("eastPixel");
		String westStr = request.getParameter(Keys.PixelBounds.west);
		String widthStr = request.getParameter(Keys.PixelBounds.width);
		String heightStr = request.getParameter(Keys.PixelBounds.height);
		try{
			int north = Integer.parseInt(northStr);
//			int south = Integer.parseInt(southStr);
//			int east = Integer.parseInt(eastStr);
			int west = Integer.parseInt(westStr);
			int width = Integer.parseInt(widthStr);
			int height = Integer.parseInt(heightStr);
			return new Rectangle(west, north, width, height);
		}catch(NumberFormatException e){
			throw new InvalidParameterException("northPixel: " + northStr + ", westPixel: "
					+ westStr + ", width: " + widthStr + ", height: " + heightStr);
		}
	}
	
	public static LatLngBoundsGM getLatLngBounds(ServletRequest request){
		//obs: width = east-west nao funciona no caso em q a emenda do mapa esta aparecendo na tela (a linha entre Japao e EUA) 
		//width seria negativo ja q west>east
		//por isso o width e o height estao sendo calculado no javascript, usando as coordenadas do centro da tela
		String northStr = request.getParameter(Keys.LatLngBounds.north);
		String southStr = request.getParameter(Keys.LatLngBounds.south);
		String eastStr = request.getParameter(Keys.LatLngBounds.east);
		String westStr = request.getParameter(Keys.LatLngBounds.west);
		String widthStr = request.getParameter(Keys.LatLngBounds.width);
		String heightStr = request.getParameter(Keys.LatLngBounds.height);
		try{
			double north = Double.parseDouble(northStr);
			double south = Double.parseDouble(southStr);
			double east = Double.parseDouble(eastStr);
			double west = Double.parseDouble(westStr);
			double width = Double.parseDouble(widthStr);
			double height = Double.parseDouble(heightStr);
			return new LatLngBoundsGM(north, south, east, west, width, height);
		}catch(NumberFormatException e){
			throw new InvalidParameterException("parametro faltando no getLimitesPixel. " +
					"northLatLng: " + northStr + ", southLatLng: " + southStr + ", eastLatLng: "
					+ eastStr + ", westLatLng: " + westStr + ", widthLatLng: " + widthStr
					+ ", heightLatLng: " + heightStr );
		}
	}
}
