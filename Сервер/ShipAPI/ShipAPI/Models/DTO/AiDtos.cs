namespace ShipAPI.Models
{
    public class ChatMessageDto
    {
        public string Role { get; set; }
        public string Content { get; set; }
    }

    public class ChatContextDto
    {
        public string City { get; set; }
        public string Schedule { get; set; }
    }

    public class ChatRequest
    {
        public List<ChatMessageDto> Messages { get; set; }
        public ChatContextDto Context { get; set; } 
    }
}