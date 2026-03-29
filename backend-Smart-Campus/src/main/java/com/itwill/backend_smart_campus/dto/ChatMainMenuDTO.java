package com.itwill.backend_smart_campus.dto;


import com.itwill.backend_smart_campus.entity.ChatMainMenu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ChatMainMenuDTO {

    private int menuId;
	private String menuName;
	private String menuLink;
	private int menuDisplayOrder;

	public static ChatMainMenuDTO toDto(ChatMainMenu chatMainMenu) {
		return ChatMainMenuDTO.builder()
				.menuId(chatMainMenu.getMenuId())
				.menuName(chatMainMenu.getMenuName())
				.menuLink(chatMainMenu.getMenuLink())
				.menuDisplayOrder(chatMainMenu.getMenuDisplayOrder())
				.build();
	} 

}


