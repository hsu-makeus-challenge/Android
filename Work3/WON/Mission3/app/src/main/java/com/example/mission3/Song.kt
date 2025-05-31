package com.example.mission3

data class Song(
    val title: String = "",
    val singer: String = "",
    var second: Int = 0,
    var playTime: Int = 60,
    var isPlaying: Boolean = false,
    var isLike: Boolean = false,  // 좋아요 상태 추가
    val id: Int = 0,  // Primary key를 수동으로 관리
    val imageRes: Int = 0 // 이미지 리소스를 추가 (이미지 리소스 ID)
)