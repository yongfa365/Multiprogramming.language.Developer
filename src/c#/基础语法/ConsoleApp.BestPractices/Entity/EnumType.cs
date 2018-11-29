using System;

namespace ConsoleApp.BestPractices
{
    public enum ColorType
    {
        Red,
        Black,
        Green,
    }

    public enum ColorType2
    {
        Red = 1,
        Black = 2,
        Green = 3,
    }

    [Flags]
    public enum ProductType
    {
        Default = 0,
        Hotel = 1 << 0, //1
        Flight = 1 << 1, //2
        Bus = 1 << 2, //4
        HotelFlight = Hotel | Flight, //3
        FlightBus = Flight | Bus //6
    }
}
