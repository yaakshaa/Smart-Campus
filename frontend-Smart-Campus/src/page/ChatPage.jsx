import React, { useState } from 'react'
import ChatHeader from '../layout/ChatHeader'
import ChatHistory from '../components/ChatHistory'
import ChatMainMenu from '../components/ChatMainMenu'
import ChatInquiryAnswer from '../components/ChatInquiryAnswer'


function ChatPage() {
  return (
    <div>
        <ChatHeader/>
        <ChatHistory/>
    </div>
  )
}

export default ChatPage
