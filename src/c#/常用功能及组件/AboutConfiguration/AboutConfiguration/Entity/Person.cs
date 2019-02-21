namespace AboutConfiguration.Entity
{
    public class Person
    {
        public string Name { get; set; }
        public int Age { get; set; }
        public Person Son { get; set; }
        public override string ToString()
        {
            return $"{{ Name:{Name} , Age:{Age} }}";
        }
    }
}
