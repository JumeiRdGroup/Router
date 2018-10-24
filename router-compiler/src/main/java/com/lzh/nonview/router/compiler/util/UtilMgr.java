package com.lzh.nonview.router.compiler.util;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class UtilMgr {

	private Elements elementUtils = null;
	private Filer filer = null;
	private Messager messager = null;
	private Types typeUtils = null;

	private static UtilMgr mgr = new UtilMgr();

	public void init (ProcessingEnvironment environment) {
		setElementUtils(environment.getElementUtils());
		setFiler(environment.getFiler());
		setMessager(environment.getMessager());
		setTypeUtils(environment.getTypeUtils());
	}

	private UtilMgr() {
	}

	public static UtilMgr getMgr() {
		return mgr;
	}

	public Elements getElementUtils() {
		return elementUtils;
	}

	public void setElementUtils(Elements elementUtils) {
		this.elementUtils = elementUtils;
	}

	public Filer getFiler() {
		return filer;
	}

	public void setFiler(Filer filer) {
		this.filer = filer;
	}

	public Messager getMessager() {
		return messager;
	}

	public void setMessager(Messager messager) {
		this.messager = messager;
	}

	public Types getTypeUtils() {
		return typeUtils;
	}

	public void setTypeUtils(Types typeUtils) {
		this.typeUtils = typeUtils;
	}

}
