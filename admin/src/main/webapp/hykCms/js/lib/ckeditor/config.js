/**
 * @license Copyright (c) 2003-2018, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	// %REMOVE_START%
	config.image_previewText = ' '; //清空预览区域显示内容
	config.filebrowserImageUploadUrl = rootUrl+"/uploadFile/uploadCkEditor";//设置提交上传图片按钮处理URL
	config.plugins =
		//'about,' +
		//'a11yhelp,' +
			'basicstyles,' +
			'bidi,' +
			'blockquote,' +
			'clipboard,' +
			'colorbutton,' +
			'colordialog,' +
			'copyformatting,' +
			'contextmenu,' +
			'dialogadvtab,' +
			'div,' +
			'elementspath,' +
			'enterkey,' +
			'entities,' +
			'filebrowser,' +
				//'find,' +//搜索
				//'flash,' +//视频文件
			'floatingspace,' +
			'font,' +
			'format,' +
				//'forms,' +
			'horizontalrule,' +
			'htmlwriter,' +
			'image,' +
				//'iframe,' +
			'indentlist,' +
			'indentblock,' +
			'justify,' +
				//'language,' +//语音
				//'link,' +//超链接
			'list,' +
			'liststyle,' +
			'magicline,' +
			'maximize,' +
				//'newpage,' +//新页面
				//'pagebreak,' +//分页打印符号
			'pastefromword,' +
			'pastetext,' +
				//'preview,' +//预览
				//'print,' +//打印
			'removeformat,' +
			'resize,' +
				//'save,' + //保存
			'selectall,' +
			'showblocks,' +
			'showborders,' +
			'smiley,' +
			'sourcearea,' +
			'specialchar,' +
			'stylescombo,' +
			'tab,' +
				//'table,' +
				//'tableselection,' +
				//'tabletools,' +
				//'templates,' + //模板
			'toolbar,' +
			'undo,' +
			'uploadimage,' +
			'wysiwygarea';
	// %REMOVE_END%
};

// %LEAVE_UNMINIFIED% %REMOVE_LINE%
