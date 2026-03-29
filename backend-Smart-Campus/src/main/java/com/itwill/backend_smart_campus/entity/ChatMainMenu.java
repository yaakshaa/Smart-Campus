package com.itwill.backend_smart_campus.entity;

import com.itwill.backend_smart_campus.dto.ChatMainMenuDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="chat_Main_Menu")
public class ChatMainMenu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "menu_id")
    private Integer menuId;

	@Column(name = "menu_name")
	private String menuName;

	@Column(name = "menu_link")
	private String menuLink;

	@Column(name = "menu_display_order")
	private Integer menuDisplayOrder;

	public static ChatMainMenu toEntity(ChatMainMenuDTO chatMainMenuDto) {
		return ChatMainMenu.builder()
				.menuId(chatMainMenuDto.getMenuId())
				.menuName(chatMainMenuDto.getMenuName())
				.menuLink(chatMainMenuDto.getMenuLink())
				.menuDisplayOrder(chatMainMenuDto.getMenuDisplayOrder())
				.build();
	} 	
    
}
