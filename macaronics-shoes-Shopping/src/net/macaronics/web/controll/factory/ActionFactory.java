package net.macaronics.web.controll.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.macaronics.web.controll.CartDeleteAction;
import net.macaronics.web.controll.CartDeleteAjaxAction;
import net.macaronics.web.controll.CartInsertAction;
import net.macaronics.web.controll.CartInsertAjax;
import net.macaronics.web.controll.CartListAction;
import net.macaronics.web.controll.IndexAction;
import net.macaronics.web.controll.JoinFormAction;
import net.macaronics.web.controll.LogOutAction;
import net.macaronics.web.controll.LoginFormAction;
import net.macaronics.web.controll.ProductDetailAction;
import net.macaronics.web.controll.ProductKindAction;
import net.macaronics.web.controll.action.Action;

public class ActionFactory {
	final Logger logger =LogManager.getLogger(ActionFactory.class);
	//싱글톤설정
	private static ActionFactory instance;
	private ActionFactory(){
		super();
	}
	public static ActionFactory getInstance(){
		if(instance==null){
			instance=new ActionFactory();
		}
		return instance;
	}
	
	
	//command 에서 넘어온 파라미터 값이 존재하면 실행
	//즉 ,존재하면 url 만 실행된다. 
	public Action getAction(String command){
		Action action=null;
		logger.info("ActionFactory : {}  ", command);
		
		if(command.equals("index")) action=new IndexAction();  
		else if(command.equals("product_detail")) action=new ProductDetailAction();//상품 상세보기
		else if(command.equals("category")) action=new ProductKindAction(); //상품종류별 보기
		else if(command.equals("join_form"))action=new JoinFormAction(); //회워가입
		else if(command.equals("login_form"))action=new LoginFormAction(); //로그인
		else if(command.equals("logout")) action=new LogOutAction(); //로그아웃
		else if(command.equals("cart_insert"))action=new CartInsertAction(); //장바구니에 담기
		else if(command.equals("cart_list")) action=new CartListAction(); //장바구니 목록
		else if(command.equals("cart_delete")) action=new CartDeleteAction(); //장바구니 삭제
		else if(command.equals("cart_ajax")) action=new CartInsertAjax(); //장바구니 ajax  담기
		else if(command.equals("cart_delete_ajax")) action=new CartDeleteAjaxAction(); //장바구니 ajax  삭제
		
		return action;
	}
	
	
	
	
}